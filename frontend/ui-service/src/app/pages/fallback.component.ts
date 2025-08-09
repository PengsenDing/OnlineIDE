import {Component} from "@angular/core";

@Component({
    selector: "fallback-page",
    template: `
        <h1>Ooohps!</h1>
        <p>Something went wrong or you are not logged in yet!</p>
    `,
    standalone: true,
})
export class FallbackPageComponent {}
