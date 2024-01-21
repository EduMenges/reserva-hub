import { api } from "$lib";
import { redirect, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import { zfd } from "zod-form-data";
import type { PageServerLoad } from "./$types";
import { message, setError, superValidate } from "sveltekit-superforms/server";
import { RestMethods } from "$lib/ApiHelpers";
import { ErrorStatusSchema, errorSchema } from "$lib/schemas";
import type { ErrorStatus } from "$lib/ApiTypes";
import { loginSchema } from "$lib/schemas";

const responseSchema = z.union([
    z.object({
        token: z.string(),
        tokenType: z.string(),
        expirationDate: z.date(),
        role: z.union([z.literal("STUDENT"), z.literal("ADMIN")]),
    }),
    errorSchema,
]);

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

        const body = await api.call<z.infer<typeof responseSchema>>(
            RestMethods.POST,
            "user/login",
            JSON.stringify(form.data)
        );

        if (ErrorStatusSchema.safeParse(body.status).success) {
            return message(form, body.data, {
                status: body.status as ErrorStatus,
            });
        }

        const stringUser = JSON.stringify(body);

        cookies.set("jwt", stringUser, { path: "/" });

        return redirect(307, "/");
    },
    as_admin: () => {
        return redirect(303, "/admin");
    },
};
