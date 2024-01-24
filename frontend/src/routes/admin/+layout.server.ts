import { error } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";

export const load = (async ({ locals }) => {
    if (!locals.user || locals.user.role !== "ADMIN") {
        throw error(401);
    }
    return {};
}) satisfies LayoutServerLoad;
