import { errorCodesSchema, errorSchema, okCodesSchema, schema } from "./schemas";
import type { NumericRange } from "@sveltejs/kit";
import { z } from "zod";

export type Error = z.infer<typeof errorSchema>;

export type ErrorCodes = z.infer<typeof errorCodesSchema>;

export type OkCodes = z.infer<typeof okCodesSchema>;

export type User = z.infer<typeof schema.user>;

export type Rooms = z.infer<typeof schema.rooms>;

export type EntryStatus = z.infer<typeof schema.entryStatus>;