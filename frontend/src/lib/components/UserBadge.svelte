<script lang="ts">
    import type { User } from "$lib/ApiTypes";
    import { Badge } from "@sveltestrap/sveltestrap";

    export let user: User
</script>

<Badge color={user.role === "ADMIN" ? "danger" : user.role === "TEACHER" ? "warning" : "primary"}>
    {user.role}
</Badge>