import { forms, schema } from "$lib/schemas";
import { fail, type Actions, error } from "@sveltejs/kit";
import { message, superValidate } from "sveltekit-superforms/server";
import type { PageServerLoad } from "./$types";
import { RestMethods, call, get } from "$lib/ApiHelpers";
import type { Rooms } from "$lib/ApiTypes";

export const load = (async ({ locals, url }) => {
    if (!locals.user) {
        throw error(401);
    }

    const searchForm = await superValidate(url, forms.roomFilter.sourceType());
    const bookForm = await superValidate(forms.roomBooking.sourceType());

    if (!searchForm.valid) {
        return { searchForm, bookForm };
    }

    const urlObject = new URL(url);
    url.searchParams.forEach((value, key) => {
        if (value === "") {
            urlObject.searchParams.delete(key, value);
        }
    });

    const body = await get<Rooms, Error>("room/filter", urlObject.search, locals.user.token);

    if ("error" in body) {
        throw error(body.status, body.error);
    }

    const rooms = schema.rooms.parse(body.data);

    return { searchForm, rooms, bookForm };
}) satisfies PageServerLoad;

export const actions: Actions = {
    allocateRoom: async ({ request, locals }) => {
        const bookForm = await superValidate(request, forms.roomBooking.sourceType());

        if (!bookForm.valid) {
            return fail(400, { bookForm });
        }

        bookForm.data.roomId = 69;

        const body = await call<any, Error>(
            RestMethods.POST,
            "reservation/request",
            JSON.stringify(bookForm.data),
            locals.user?.token
        );

        if ("error" in body) {
            return message(bookForm, body.error, { status: body.status });
        }

        return message(bookForm, "Reservado com sucesso!");
    },
};
