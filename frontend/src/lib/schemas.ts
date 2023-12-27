import { z } from "zod";

export enum Status {
    Pending = "Pending",
    Confirmed = "Confirmed",
    Canceled = "Canceled"
};

export const BookingSchema = z.object(
    {
        startDate: z.date(),
        endDate: z.date(),
        name: z.string(),
        description: z.string(),
        room: z.string(),
        status: z.nativeEnum(Status)
    }
);

export type BookingType =
    z.infer<typeof BookingSchema>;