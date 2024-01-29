import type { PageServerLoad } from "./$types";
import { error } from "@sveltejs/kit";
import { GetHistory } from "$lib/ApiEndpoints";

export const load = (async ({ locals }) => {
    if (!locals.user) {
        throw error(401);
    }

    const history = await GetHistory(locals.user.token);

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
