import { z } from "zod";

const userSchema = z.object({
    username: z.string(),
    token: z.string(),
});

export type User = z.infer<userSchema>;
