import { error } from "@sveltejs/kit";
import type { LayoutServerLoad } from "./$types";
import { GetHistory } from "$lib/ApiEndpoints";

export const load = (async ({ locals }) => {
    if (!locals.user || locals.user.role !== "ADMIN") {
        throw error(401);
    }

    const history = await GetHistory(locals.user?.token as string);

    return {
        history: history.map((entry) => ({
            userInfo: entry.userInfo,
            roomInfo: entry.roomInfo,
            entryMapping: entry.entryMapping,
            eventName: entry.eventName,
            status: entry.status,
            startDate: new Date(`${entry.date}T${entry.startTime}`),
            endDate: new Date(`${entry.date}T${entry.endTime}`),
        })),
    };
}) satisfies LayoutServerLoad;
