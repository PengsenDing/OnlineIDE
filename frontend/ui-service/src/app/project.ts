import {File} from "./file";

export interface Project {
    id: string;
    name: string;
    files?: File[];
}
