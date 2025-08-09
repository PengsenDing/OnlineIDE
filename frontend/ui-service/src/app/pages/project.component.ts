import {Component, effect, inject} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {MonacoEditorModule} from "ngx-monaco-editor-v2";
import {DarkModeService} from "../services/darkmode.service";
import { ProjectService } from "../services/project.service";
import { File } from "../file";
import { ActivatedRoute } from "@angular/router";
import { CompilerService } from "../services/compiler.service";
import { tap } from "rxjs";

@Component({
    selector: "project-page",
    template: `
        <div class="d-flex gap-2">
            <div class="file-view">
                <div class="input-group">
                    <input
                            type="text"
                            class="form-control"
                            placeholder="New File"
                            [(ngModel)]="newFileName"
                    />
                    <div class="input-group-append">
                        <button
                                [disabled]="!newFileName"
                                class="btn btn-outline-secondary"
                                type="button"
                                (click)="addProject()"
                        >
                            Create
                        </button>
                    </div>
                </div>
                <div class="list-group mt-2">
                    @for (file of orderFiles(); track file?.id) {
                        <button 
                            class="list-group-item list-group-item-action d-flex" 
                            (click)="setActiveFile(file)"
                            [class.active]="activeFile?.id == file.id"
                        >
                            {{file.name}}
                            <button class="ms-auto btn btn-outline-danger btn-sm" (click)="projectService.deleteFile(file.id)">
                                <i class="bi bi-trash"></i>
                            </button>
                        </button>
                    }
                    @if (orderFiles().length == 0) {
                        <span class="text-muted">No files yet.</span>
                    }
                </div>
            </div>
            <div class="code-view d-flex flex-column">
                <ngx-monaco-editor
                        class="custom-monaco-editor border p-2"
                        [options]="editorOptions"
                        [(ngModel)]="code"
                ></ngx-monaco-editor>
                <div class="ms-auto d-flex gap-1">
                    <button 
                        class="btn btn-outline-primary btn-sm m-1" 
                        (click)="compile()"
                        [disabled]="!code || code != activeFile?.code"
                    >
                        Compile
                    </button>
                    <button 
                        [disabled]="!activeFile || code == activeFile.code || (!activeFile.code && !code)" 
                        class="btn btn-primary btn-sm m-1" 
                        (click)="saveFile()"
                    >
                        Save
                    </button>
                </div>
                <div class="compiler-view border p-2" [innerHTML]="compilerOutput"></div>
            </div>
        </div>
    `,
    styles: `
        .file-view {
            width: 20vw;
            overflow: auto;
        }

        .code-view {
            width: calc(80vw - 1.5rem);
        }

        .custom-monaco-editor {
            height: 70vh;
            width: 100%
        }

        .compiler-view {
            min-height: 180px;
            width: 100%;
            overflow: auto;
        }
    `,
    imports: [MonacoEditorModule, FormsModule],
    standalone: true,
})
export class ProjectPageComponent {
    darkModeService = inject(DarkModeService);
    projectService = inject(ProjectService);
    route = inject(ActivatedRoute);
    compilerService = inject(CompilerService);

    isDarkMode = this.darkModeService.isDarkMode

    newFileName = ""
    activeFile?: File;

    editorOptions = { theme: "vs-light", language: "java" };
    code = "";

    compilerOutput = "<span class=\"text-muted\">No compiler output...</span>";

    constructor() {
        this.projectService.getFiles(this.route.snapshot.params["projectId"]);
        effect(() => {
            this.editorOptions = { ...this.editorOptions, theme: this.isDarkMode() ? 'vs-dark' : 'vs-light' }
        })
    }

    orderFiles() {
        return this.projectService.files()?.sort(((a, b) => a.name.localeCompare(b.name))) ?? []
    }

    addProject() {
        this.projectService.addFile(this.newFileName, this.route.snapshot.params["projectId"]);
        this.newFileName = "";
    }

    setActiveFile(file: File) {
        this.activeFile = file;
        this.code = file.code;
    }

    saveFile() {
        if (this.activeFile?.id) {
            this.projectService.updateFile(this.activeFile?.id, this.code);
        }
    }

    compile() {
        if (this.activeFile) {
            this.compilerService.compile(this.activeFile?.code, this.activeFile?.name).pipe(
                tap(response => {
                    const message = response?.compilable 
                        ? `<span class="text-success">The file ${response.fileName} compiled successfully!</span>` 
                        : `<span class="text-danger">The file ${response?.fileName} did not compile correctly!</span>`
                    const stderr = response?.stderr.replace("\n", "<br/>") ?? "";

                    this.compilerOutput = message + "<br/><br/>" + stderr;
                })
            ).subscribe()
        }
    }
}
