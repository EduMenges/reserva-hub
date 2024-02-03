import { error, type Actions, fail } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";
import { RestMethods, call } from "$lib/ApiHelpers";
import { z } from "zod";
import { type Entity, type Error } from "$lib/ApiTypes.d";
import { responses, schema } from "$lib/schemas";

export const load = (async () => {
    return {};
}) satisfies PageServerLoad;

async function boilerPlate(
    token: string | undefined,
    request: Request,
    endpoints: { reservation: string; edit: string }
) {
    if (token === undefined) {
        throw error(501);
    }

    const form = await request.formData();

    const id = z.coerce.number().parse(form.get("id"));
    const type = schema.entityType.parse(form.get("type"));
    const payload =
        type === "RESERVATION_REQUEST"
            ? JSON.stringify({ reservationId: id })
            : JSON.stringify({ editionRequestId: id });

    const endpoint = type === "RESERVATION_REQUEST" ? endpoints.reservation : endpoints.edit;

    const body = await call<any, Error>(RestMethods.POST, endpoint, payload, token);

    if ("error" in body) {
        return fail(body.status, body.error);
    }

    const responseSchema = type === "RESERVATION_REQUEST" ? responses.approval : responses.edit;

    const response = responseSchema.parse(body.data);

    return { response };
}

export const actions: Actions = {
    postApprove: async ({ locals, request }) => {
        return boilerPlate(locals.user?.token, request, { reservation: "reservation/approve", edit: "edit/approve" });
    },
    postDelete: async ({ locals, request }) => {
        return boilerPlate(locals.user?.token, request, { reservation: "reservation/cancel", edit: "edit/cancel" });
    },
};
