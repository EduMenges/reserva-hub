<script lang="ts">
    import { schema } from "$lib/schemas";

    export let status: schema.EntryStatus;
</script>

{#if status === "APPROVED"}
<span class="text-success-emphasis">Aprovado</span>
{:else if status === "AWAITING_APPROVAL"}
<span class="text-warning-emphasis">Aguardando aprovação</span>
{:else if status === "CANCELED"}
<span class="text-danger-emphasis">Cancelado</span>
{:else if status === "ACTIVE"}
<span class="text-success-emphasis">Ativo</span>
{:else if status === "DENIED"}
<span class="text-danger-emphasis">Negado</span>
{/if}