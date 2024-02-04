import { RestMethods, call } from "$lib/ApiHelpers";
import type { Error } from "$lib/ApiTypes";
import { responses } from "$lib/schemas";
import { fail, type Actions } from "@sveltejs/kit";

export const actions: Actions = {
    cancel: async ({ locals, request }) => {
        const editionRequestId = Number((await request.formData()).get("editionRequestId"));

        const body = await call<any, Error>(
            RestMethods.POST,
            "edit/cancel",
            JSON.stringify({ editionRequestId }),
            locals.user?.token
        );

        if ("error" in body) {
            return fail(body.status, body.error);
        }

        const response = responses.edit.parse(body.data);

        return { response };
    },
};
