import { message, superValidate } from "sveltekit-superforms/server";
import type { PageServerLoad } from "./$types";
import { forms, loginSchema, responses, schema } from "$lib/schemas";
import { error, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import { hours } from "$lib/utils";
import type { Error } from "$lib/ApiTypes";
import { api } from "$lib";
import { RestMethods } from "$lib/ApiHelpers";

export const load = (async ({ locals, params, parent }) => {
    const fullHistory = (await parent()).history;

    const history = fullHistory.find((entry) => entry.entryMapping.entityId === Number(params.id));

    if (history === undefined) {
        throw error(404);
    }

    const data: z.infer<typeof forms.editReservation> = {
        date: history.startDate.toISOString().split("T")[0],
        eventName: history.name,
        eventDescription: "",
        startTime: hours(history.startDate),
        endTime: hours(history.endDate),
        reservationId: history.entryMapping.entityId,
        roomId: 69,
    };

    const form = await superValidate(data, forms.editReservation.sourceType());

    return { form };
}) satisfies PageServerLoad;

export const actions: Actions = {
    async edit({ locals, request }) {
        const form = await superValidate(request, forms.editReservation.sourceType());

        if (!form.valid) {
            return fail(400, { form });
        }

        const body = await api.call<any, Error>(
            RestMethods.POST,
            "edit/request",
            JSON.stringify(form.data),
            locals.user?.token
        );

        if ("error" in body) {
            return message(form, body.error, { status: body.status });
        }

        const response = responses.edit.parse(body.data);

        return { response };
    },
    async delete({ request, locals }) {
        const form = await superValidate(request, forms.editReservation.sourceType());

        if (form.errors.reservationId) {
            return fail(400, { form });
        }

        const reservationId = form.data.reservationId;

        const body = await api.call<any, Error>(
            RestMethods.POST,
            "reservation/cancel",
            JSON.stringify({ reservationId }),
            locals.user?.token
        );

        if ("error" in body) {
            return message(form, body.error, { status: body.status });
        }

        const response = responses.approval.parse(body.data);

        return { response };
    },
};
