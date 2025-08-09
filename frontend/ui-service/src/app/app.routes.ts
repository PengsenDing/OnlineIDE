import {Routes} from "@angular/router";
import {ProjectsPageComponent} from "./pages/projects.component";
import {ProjectPageComponent} from "./pages/project.component";
import {AuthGuard} from "./auth.guard";
import {FallbackPageComponent} from "./pages/fallback.component";

export const routes: Routes = [
    {
        path: "",
        redirectTo: "projects",
        pathMatch: "full"
    },
    {
        title: "Projects",
        path: "projects",
        component: ProjectsPageComponent,
        canActivate: [AuthGuard],
        providers: [AuthGuard]
    },
    {
        title: "Project",
        path: "projects/:projectId",
        component: ProjectPageComponent,
        canActivate: [AuthGuard],
        providers: [AuthGuard]
    },
    {
        path: "**",
        component: FallbackPageComponent
    }
];
