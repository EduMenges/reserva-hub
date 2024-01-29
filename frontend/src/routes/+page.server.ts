import { redirect } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";

export const load = (async ({ locals }) => {
    if (!locals.user) {
        redirect(303, "/login");
    }

    if (locals.user.role === "ADMIN") {
        redirect(303, "/admin");
    }

    return {};
}) satisfies PageServerLoad;
