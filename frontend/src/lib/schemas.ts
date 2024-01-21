import { z } from "zod";
import { zfd } from "zod-form-data";

export enum Status {
    Pending = "Pending",
    Confirmed = "Confirmed",
    Canceled = "Canceled",
}

export const errorSchema = z.object({ status: z.string(), errors: z.array(z.string()) });

export const BookingSchema = z.object({
    startDate: z.date(),
    endDate: z.date(),
    name: z.string(),
    description: z.string(),
    room: z.string(),
    status: z.nativeEnum(Status),
});

export type BookingType = z.infer<typeof BookingSchema>;

export const RoomSchema = z.object({
    name: z.string(),
    building: z.string(),
    vacancy: z.number(),
});

export const OkStatusSchema = z.number().min(100).max(399);
export const ErrorStatusSchema = z.number().min(400).max(599);

export type RoomType = z.infer<typeof RoomSchema>;

export const loginSchema = zfd.formData({
    username: zfd.text(z.string({ required_error: "Seu nome de usuário é necessário" })),
    password: zfd.text(z.string({ required_error: "Sua senha é necessária" })),
});