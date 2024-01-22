import { api } from "$lib";
import { redirect, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import type { PageServerLoad } from "./$types";
import { message, superValidate } from "sveltekit-superforms/server";
import { RestMethods } from "$lib/ApiHelpers";
import { errorSchema, maybeError } from "$lib/schemas";
import type { Error } from "$lib/ApiTypes";
import { loginSchema } from "$lib/schemas";

const responseSchema = z.object({
    token: z.string(),
    tokenType: z.string(),
    expirationDate: z.date(),
    role: z.union([z.literal("STUDENT"), z.literal("ADMIN")]),
})

type Response = z.infer<typeof responseSchema>;

export const load = (async ({ request }) => {
    const form = await superValidate(request, loginSchema.sourceType());
    return { form };
}) satisfies PageServerLoad;

export const actions: Actions = {
    login: async ({ request, cookies }) => {
        const form = await superValidate(request, loginSchema.sourceType());

        if (!form.valid) {
            return fail(400, { form });
        }

        const body = await api.call<z.infer<(typeof responseSchema)>, Error>(
            RestMethods.POST,
            "user/login",
            JSON.stringify(form.data)
        );

        const errorBody = maybeError(errorSchema).safeParse(body);

        if (errorBody.success) {
            return message(form, errorBody.data.data, { status: errorBody.data.status })
        }

        const okBody = body.data as Response;

        const stringUser = JSON.stringify(body.data);

        cookies.set("jwt", stringUser, { path: "/", expires: okBody.expirationDate });

        return redirect(307, "/");
    },
    as_admin: () => {
        return redirect(303, "/admin");
    },
};
