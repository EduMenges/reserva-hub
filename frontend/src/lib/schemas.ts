import { z } from "zod";
import { zfd } from "zod-form-data";

export function isValidZodLiteralUnion<T extends z.ZodLiteral<unknown>>(literals: T[]): literals is [T, T, ...T[]] {
    return literals.length >= 2;
}

export function constructZodLiteralUnionType<T extends z.ZodLiteral<unknown>>(literals: T[]) {
    if (!isValidZodLiteralUnion(literals)) {
        throw new Error(
            "Literals passed do not meet the criteria for constructing a union schema, the minimum length is 2"
        );
    }

    return z.union(literals);
}

export const okCodes = [
    100, 101, 102, 103, 200, 201, 202, 203, 204, 205, 206, 207, 208, 226, 300, 301, 302, 303, 304, 305, 306, 307, 308,
] as const;

export const errorCodes = [
    400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 421, 422, 423, 425,
    426, 428, 429, 431, 451, 500, 501, 502, 503, 504, 505, 506, 507, 508, 510, 511,
] as const;

export const errorCodesSchema = constructZodLiteralUnionType(errorCodes.map((literal) => z.literal(literal)));

export const okCodesSchema = constructZodLiteralUnionType(okCodes.map((literal) => z.literal(literal)));

export const errorSchema = z.object({ status: z.string(), errors: z.string().array() });

export function maybeError(errorSchema: z.AnyZodObject) {
    return z.object({ data: errorSchema, status: errorCodesSchema });
}

export const roomSchema = z.object({
    name: z.string(),
    building: z.string(),
    vacancy: z.number(),
});

export const okStatusSchema = z.number().min(100).max(399);
export const errorStatusSchema = z.number().min(400).max(599);

export type RoomType = z.infer<typeof roomSchema>;

export const loginSchema = zfd.formData({
    username: zfd.text(z.string({ required_error: "Seu nome de usuário é necessário" })),
    password: zfd.text(z.string({ required_error: "Sua senha é necessária" })),
});

export const userSchema = z.object({
    token: z.string(),
    tokenType: z.string(),
    expirationDate: z.coerce.date(),
    role: z.union([z.literal("STUDENT"), z.literal("ADMIN")]),
});


export namespace schema {
    const role = z.enum(["STUDENT", "TEATCHER", "ADMIN"]);

    const userInfo = z.object({
        username: z.string(),
        userId: z.number(),
        role
    })

    const roomInfo = z.object({
        roomNumber: z.string(),
        buildingNumber: z.string()
    })

    const entityType = z.enum(["RESERVATION", "RESERVATION_REQUEST", "EDITION_REQUEST"])

    const entryStatus = z.enum(["ACTIVE",
        "EXPIRED",
        "CANCELED",
        "AWAITING_APPROVAL",
        "APPROVED",
        "DENIED"])

    export type EntryStatus = z.infer<typeof entryStatus>;

    const entryMapping = z.object({
        type: entityType,
        entityId: z.number()
    })

    export const userHistoryEntry = z.object({
        userInfo,
        roomInfo,
        entryMapping,
        eventName: z.string(),
        status: entryStatus,
        date: z.string(),
        startTime: z.string(),
        endTime: z.string(),
    }).array()
}