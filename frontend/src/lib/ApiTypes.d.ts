import { errorSchema } from "./schemas";
import { NumericRange } from "@sveltejs/kit";

export type Error = z.infer<typeof errorSchema>;

export type ErrorStatus = NumericRange<400, 599>;

export type OkStatus = NumericRange<100, 399>;
