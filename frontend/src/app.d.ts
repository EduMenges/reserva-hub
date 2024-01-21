// See https://kit.svelte.dev/docs/types#app

import type { BookingType } from "$lib/schemas";
import type * as Api from "$lib/ApiTypes";

// for information about these interfaces
declare global {
    namespace App {
        // interface Error {}
        // interface Locals {}
        // interface PageData {}
        // interface PageState {}
        // interface Platform {}
        namespace Superforms {
            type Message = Api.Error;
        }
    }
}

export {};
