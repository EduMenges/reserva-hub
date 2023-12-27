<script lang="ts">
    import { Status } from "$lib/schemas";

    export let status: Status;
</script>

{#if status === Status.Confirmed}
<span class="text-success-emphasis">Confirmado</span>
{:else if status === Status.Pending}
<span class="text-warning-emphasis">Pendente</span>
{:else if status === Status.Canceled}
<span class="text-danger-emphasis">Cancelado</span>
{/if}