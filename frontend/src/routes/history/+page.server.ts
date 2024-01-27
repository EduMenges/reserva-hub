import { RestMethods, call } from "$lib/ApiHelpers";
import { schema } from "$lib/schemas";
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

    const history = schema.userHistoryEntry.parse(historyReply.data);

    return {
        history: history.map(entry => ({
            status: entry.status,
            roomInfo: entry.roomInfo,
            startDate: new Date(`${entry.date}T${entry.startTime}`),
            endDate: new Date(`${entry.date}T${entry.endTime}`),
            name: entry.eventName
        }))
    };
}) satisfies PageServerLoad;
