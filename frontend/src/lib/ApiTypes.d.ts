import { errorCodesSchema, errorSchema, okCodesSchema } from "./schemas";
import type { NumericRange } from "@sveltejs/kit";
import { z } from "zod"

export type Error = z.infer<typeof errorSchema>;

export type ErrorCodes = z.infer<typeof errorCodesSchema>

export type OkCodes = z.infer<typeof okCodesSchema>
