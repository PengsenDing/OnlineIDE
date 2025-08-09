import {effect, inject, Inject, Injectable, signal, WritableSignal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";
import { DOCUMENT } from "@angular/common";

interface CompilationResponse {
    fileName: String;
    code: String;
    stderr: String;
    compilable: boolean;
}

@Injectable({providedIn: "root"})
export class CompilerService {

    private baseUrl: string = "/compiler/compile"

    http = inject(HttpClient);

    compile(code: String, fileName: String): Observable<CompilationResponse | undefined> {
        return this.http.post<CompilationResponse>(this.baseUrl, { code, fileName })
    }
}
