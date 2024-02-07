<script lang="ts">
    import { enhance } from "$app/forms";
    import StatusColored from "$lib/components/StatusColored.svelte";
    import Date from "$lib/components/table/Date.svelte";
    import TimeSpan from "$lib/components/table/TimeSpan.svelte";
    import { readableEntityType } from "$lib/utils";
    import { Alert, Tooltip } from "@sveltestrap/sveltestrap";

    export let data;
    export let form;
    let entriesWaitingApproval = data.history.filter((entry) => entry.status === "AWAITING_APPROVAL");
</script>

<svelte:head>
    <title>Aprovar Reservas</title>
</svelte:head>

{#if form !== null}
    {#if "errors" in form}
        <Alert color="danger">Erro ao submeter a ação. Você tentou hackear?</Alert>
    {:else}
        <Alert
            >O evento <strong>{form.response.eventName}</strong> está
            <strong><StatusColored status={form.response.status} /></strong></Alert
        >
    {/if}
{/if}

{#if entriesWaitingApproval.length > 0}
    <section class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th scope="col">Usuário</th>
                    <th scope="col">Data</th>
                    <th scope="col">Horário</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Sala</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                {#each entriesWaitingApproval as entry, index}
                    <tr>
                        <td>
                            {entry.userInfo.username}
                        </td>
                        <Date date={entry.startDate} />
                        <TimeSpan startTime={entry.startDate} endTime={entry.endDate} />
                        <td>
                            <span id="entry-name-{index}">{entry.eventName}</span>
                            <Tooltip target="entry-name-{index}" class="mb-2">
                                {entry.eventDescription ?? "Sem descrição"}
                            </Tooltip>
                        </td>
                        <td>
                            {entry.roomInfo.buildingNumber} — {entry.roomInfo.roomNumber}
                        </td>
                        <td>
                            {readableEntityType(entry.entryMapping.type)}
                        </td>
                        <td>
                            <form method="post" use:enhance>
                                <input readonly name="id" value={entry.entryMapping.entityId} hidden />
                                <input readonly name="type" value={entry.entryMapping.type} hidden />
                                <button class="btn btn-success" type="submit" formaction="?/postApprove">Aprovar</button>
                                <button class="btn btn-danger" type="submit" formaction="?/postDelete">Negar</button>
                            </form>
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </section>
{:else}
    <p class="text-center">Não há reservas esperando aprovação.</p>
{/if}
