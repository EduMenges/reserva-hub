import { api } from "$lib";
import { redirect, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import { zfd } from "zod-form-data";
import type { PageServerLoad } from "./$types";
import { superValidate } from "sveltekit-superforms/server";
import { type UnwrapEffects } from "sveltekit-superforms";

const loginSchema = zfd.formData({
    username: zfd.text(z.string({ required_error: "Seu nome de usuário é necessário" })),
    password: zfd.text(z.string({ required_error: "Sua senha é necessária" })),
});

const responseSchema = z.object({
    token: z.string(),
    tokenType: z.string(),
    expirationDate: z.date(),
    role: z.union([z.literal("STUDENT"), z.literal("ADMIN")]),
});

export const load = (async ({ request }) => {
    const form = await superValidate(request, loginSchema.sourceType());

    return { form };
}) satisfies PageServerLoad;

export const actions: Actions = {
    login: async ({ request, cookies }) => {
        const form = await superValidate(request, loginSchema.sourceType());

        if (!form.valid) {
            console.log(form);
            return fail(400, { form });
        }

        const body = await api.post("user/login", form.data);

        const response = responseSchema.parse(body.errors);

        console.log(response);

        if (body.errors) {
            return fail(401, { form });
        }

        const stringUser = JSON.stringify(response);

        cookies.set("jwt", stringUser, { path: "/" });

        return redirect(307, "/");
    },
    as_admin: () => {
        return redirect(303, "/admin");
    },
};
