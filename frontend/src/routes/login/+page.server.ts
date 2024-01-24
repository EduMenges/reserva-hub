import { api } from "$lib";
import { redirect, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import type { PageServerLoad } from "./$types";
import { message, superValidate } from "sveltekit-superforms/server";
import { RestMethods } from "$lib/ApiHelpers";
import { errorSchema, maybeError } from "$lib/schemas";
import type { Error, User } from "$lib/ApiTypes";
import { loginSchema } from "$lib/schemas";

export const load = (async ({ request, locals }) => {
    if (locals.user) {
        return redirect(307, "/");
    }

    const form = await superValidate(request, loginSchema.sourceType());
    return { form };
}) satisfies PageServerLoad;

export const actions: Actions = {
    login: async ({ request, cookies }) => {
        const form = await superValidate(request, loginSchema.sourceType());

        if (!form.valid) {
            return fail(400, { form });
        }

        const body = await api.call<User, Error>(RestMethods.POST, "user/login", JSON.stringify(form.data));

        const errorBody = maybeError(errorSchema).safeParse(body);

        if (errorBody.success) {
            return message(form, errorBody.data.data, { status: errorBody.data.status });
        }

        const okBody = body.data as User;

        const value = btoa(JSON.stringify(okBody));

        cookies.set("jwt", value, { path: "/" });

        return redirect(307, "/");
    },
    as_admin: () => {
        return redirect(303, "/admin");
    },
};
