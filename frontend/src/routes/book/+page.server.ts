import { errorCodes, errorCodesSchema, errorSchema, forms, maybeError, schema } from "$lib/schemas";
import { fail, type Actions, error } from "@sveltejs/kit";
import { message, superValidate } from "sveltekit-superforms/server";
import type { PageServerLoad } from "./$types";
import { RestMethods, call, get } from "$lib/ApiHelpers";
import type { Rooms } from "$lib/ApiTypes";

export const load = (async ({ locals, request, url }) => {
    if (!locals.user) {
        throw error(401);
    }

    const form = await superValidate(request, forms.roomFilter.sourceType());

    if (url.search.length == 0) {
        return { form };
    }

    const body = await get<Rooms, Error>("room/filter", url.search, locals.user.token);

    if ("error" in body) {
        throw error(body.status, body.error);
    }

    const rooms = schema.rooms.parse(body.data);

    return { form, rooms };
}) satisfies PageServerLoad;

export const actions: Actions = {
    search: async ({ request }) => {
        const form = await superValidate(request, forms.roomFilter.sourceType());

        if (!form.valid) {
            return fail(400, { form });
        }
    },
    allocateRoom: async ({ request }) => {
        throw error(501);
    }
}