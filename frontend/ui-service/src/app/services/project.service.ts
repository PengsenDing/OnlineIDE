import {Injectable, Signal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {toSignal} from '@angular/core/rxjs-interop';
import {Project} from "../project";
import {BehaviorSubject, catchError, tap} from "rxjs";
import { File } from "../file";

@Injectable({providedIn: "root"})
export class ProjectService {

    private baseUrl: string = "/backend/api"

    private projectsSubject: BehaviorSubject<Project[]> = new BehaviorSubject<Project[]>([]);

    projects: Signal<Project[] | undefined> = toSignal<Project[]>(this.projectsSubject);

    private filesSubject: BehaviorSubject<File[]> = new BehaviorSubject<File[]>([]);
    files: Signal<File[] | undefined> = toSignal(this.filesSubject)

    constructor(private http: HttpClient) {
        this.getProjects()
    }

    getProjects(): void {
        this.http.get<Array<Project>>(this.baseUrl + "/projects")
            .pipe(
                tap(response => {
                    if (response) {
                        this.projectsSubject.next(response);
                    } else {
                        console.error("Oohps! Could not load projects.");
                    }
                }),
                catchError(error => {
                    console.error("Oohps! Could not load projects.");
                    throw error;
                })
            )
            .subscribe();
    }

    addProject(name: string) {
        this.http.post<Project>(`${this.baseUrl}/projects/${name}`, null)
            .pipe(
                tap(response => {
                    const projects = this.projectsSubject.getValue()
                    if (response) {
                        projects.push(response)
                    } else {
                        console.error("Ohhps! Could not create project!");
                    }
                    this.projectsSubject.next(projects)
                }),
                catchError(error => {
                    console.error("Ohhps! Could not create project!");
                    throw error;
                })
            )
            .subscribe();
    }

    getFiles(projectId: string) {
        this.http.get<Array<File>>(`${this.baseUrl}/sourceFile/project/${projectId}`)
            .pipe(
                tap(response => {
                    if (response) {
                        this.filesSubject.next(response);
                    } else {
                        console.error("Oohps! Could not load files.");
                    }
                }),
                catchError(error => {
                    console.error("Oohps! Could not load files.");
                    throw error;
                })
            )
            .subscribe();
    }

    addFile(name: String, projectId: String) {
        this.http.post<File>(`${this.baseUrl}/sourceFile/project/${projectId}`, name)
        .pipe(
            tap(response => {
                const files = this.filesSubject.getValue();
                if (response && files) {
                    files.push(response);
                } else {
                    console.error("Oohps! Could not create file.");
                }
                this.filesSubject.next(files);
            }),
            catchError(error => {
                console.error("Oohps! Could not create file.");
                throw error;
            })
        )
        .subscribe();
    }

    updateFile(id: String, code: String) {
        this.http.put<File>(`${this.baseUrl}/sourceFile/sourceFiles/${id}`, code)
        .pipe(
            tap(response => {
                const files = this.filesSubject.getValue();
                if (response && files) {
                    const updatedFile = files.find(value => value.id == id);
                    if (updatedFile) {
                        updatedFile.code = response.code
                    }
                } else {
                    console.error("Oohps! Could not update file.");
                }
                this.filesSubject.next(files);
            }),
            catchError(error => {
                console.error("Oohps! Could not update file.");
                throw error;
            })
        )
        .subscribe();
    }

    deleteFile(id: String) {
        this.http.delete(`${this.baseUrl}/sourceFile/sourceFiles/${id}`)
        .pipe(
            tap(() => {
                const files = this.filesSubject.getValue();
                if (files) {
                    const updatedFiles = files.filter((file) => file.id != id);
                    this.filesSubject.next(updatedFiles);
                } else {
                    console.error("Oohps! Could not delete file.");
                }
            }),
            catchError(error => {
                console.error("Oohps! Could not delete file.");
                throw error;
            })
        )
        .subscribe();
    }
}
