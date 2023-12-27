import { Status } from '$lib/schemas';
import type { PageServerLoad } from './$types';

export const load = (async () => {
    return {
        bookings: [{
            description: "",
            name: "Festa cristã",
            status: Status.Confirmed,
            room: "Anfiteatro",
            startDate: new Date(Date.now()),
            endDate: new Date(Date.now() + 100),
        }
        ]
    };
}) satisfies PageServerLoad;