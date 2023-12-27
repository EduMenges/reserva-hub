import { Status, type BookingType } from '$lib/schemas';
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
        },
            {
                description: "",
                name: "Festa satânica",
                status: Status.Canceled,
                room: "Instituto de Ciências Humanas",
                startDate: new Date(666),
                endDate: new Date(999999)
        }
        ]
    };
}) satisfies PageServerLoad<{ bookings: BookingType[] }>;