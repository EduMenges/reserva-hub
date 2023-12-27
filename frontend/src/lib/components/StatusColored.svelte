<script lang="ts">
    import { Status } from "$lib/schemas";

    export let status: Status;
</script>

{#if status === Status.Confirmed}
<span class="text-success-emphasis">Confirmed</span>
{:else if status === Status.Pending}
<span class="text-warning-emphasis">Pending</span>
{:else if status === Status.Canceled}
<span class="text-danger-emphasis">Canceled</span>
{/if}