import { RestMethods, call } from "$lib/ApiHelpers";
import { Status, type BookingType } from "$lib/schemas";
import type { PageServerLoad } from "./$types";
import { errorCodesSchema } from "$lib/schemas";
import { error } from "@sveltejs/kit";

export const load = (async ({ locals }) => {
    if (!locals.user) {
        throw error(401);
    }
    const historyReply = await call(RestMethods.GET, "history/get", undefined, locals.user?.token);

    const errorCode = errorCodesSchema.safeParse(historyReply.status);
    if (errorCode.success) {
        throw error(errorCode.data);
    }

    return {
        bookings: [
            {
                description: "",
                name: "Festa cristã",
                status: Status.Confirmed,
                room: "Anfiteatro",
                startDate: new Date(Date.now()),
                endDate: new Date(Date.now() + 100),
            },
            {
                description: "",
                name: "Festa satânica",
                status: Status.Canceled,
                room: "Instituto de Ciências Humanas",
                startDate: new Date(666),
                endDate: new Date(999999),
            },
        ],
    };
}) satisfies PageServerLoad<{ bookings: BookingType[] }>;
