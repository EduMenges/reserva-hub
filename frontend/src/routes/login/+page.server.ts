import { api } from "$lib";
import { redirect, type Actions, fail } from "@sveltejs/kit";
import { z } from "zod";
import type { PageServerLoad } from "./$types";
import { message, superValidate } from "sveltekit-superforms/server";
import { RestMethods } from "$lib/ApiHelpers";
import { errorSchema, maybeError, schema } from "$lib/schemas";
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
    default: async ({ request, cookies }) => {
        const form = await superValidate(request, loginSchema.sourceType());

        if (!form.valid) {
            return fail(400, { form });
        }

        const body = await api.call<User, Error>(RestMethods.POST, "user/login", JSON.stringify(form.data));

        if ("error" in body) {
            return message(form, body.error, { status: body.status });
        }

        const user = schema.user.parse(body.data);

        const value = btoa(JSON.stringify(user));

        cookies.set("jwt", value, { path: "/", expires: user.expirationDate });

        return redirect(307, "/");
    }
};
