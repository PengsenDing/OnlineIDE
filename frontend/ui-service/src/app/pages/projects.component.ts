import {Component, inject} from "@angular/core";
import {ProjectService} from "../services/project.service";
import {FormsModule} from "@angular/forms";

@Component({
    selector: "projects-page",
    template: `
        <h2>Projects</h2>
        <div class="list-group mb-3">
            @for (project of projectService.projects(); track project.id) {
                <a 
                    class="list-group-item list-group-item-action"
                    [href]="'projects/' + project.id">
                    {{ project.name }}
                </a>
            }
        </div>

        <div class="input-group">
            <input
                    type="text"
                    class="form-control"
                    placeholder="Add project"
                    [(ngModel)]="newProjectName"
            />
            <div class="input-group-append">
                <button
                        [disabled]="!newProjectName"
                        class="btn btn-outline-secondary"
                        type="button"
                        (click)="addProject()"
                >
                    Add
                </button>
            </div>
        </div>
    `,
    standalone: true,
    imports: [FormsModule],
})
export class ProjectsPageComponent {
    projectService = inject(ProjectService);
    newProjectName = "";

    addProject() {
        this.projectService.addProject(this.newProjectName);
        this.newProjectName = "";
    }
}
