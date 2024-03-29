import { errorCodesSchema, errorSchema, okCodesSchema, responses, schema } from "./schemas";
import type { NumericRange } from "@sveltejs/kit";
import { z } from "zod";

export type Error = z.infer<typeof errorSchema>;

export type ErrorCodes = z.infer<typeof errorCodesSchema>;

export type OkCodes = z.infer<typeof okCodesSchema>;

export type User = z.infer<typeof schema.user>;

export type Rooms = z.infer<typeof schema.rooms>;

export type EntryStatus = z.infer<typeof schema.entryStatus>;

export type HistoryEntry = z.infer<typeof schema.historyEntry>;

export type History = z.infer<typeof schema.fullHistory>;

export type Entity = z.infer<typeof schema.entityType>;

export enum BootstrapColors {
    PRIMARY = "primary",
    SECONDARY = "secondary",
    SUCCESS = "success",
    WARNING = "warning",
    DANGER = "danger",
    INFO = "info",
}
