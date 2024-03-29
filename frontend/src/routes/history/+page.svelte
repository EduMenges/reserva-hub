<script lang="ts">
    import { enhance } from "$app/forms";
    import StatusColored from "$lib/components/StatusColored.svelte";
    import Date from "$lib/components/table/Date.svelte";
    import TimeSpan from "$lib/components/table/TimeSpan.svelte";
    import { Alert, Tooltip } from "@sveltestrap/sveltestrap";
    import { isEditable } from "./isEditable";

    export let data;
    export let form;

    $: edits = data.history.filter((entry) => entry.entryMapping.type === "EDITION_REQUEST");
    $: entries = data.history.filter((entry) => entry.entryMapping.type !== "EDITION_REQUEST");
</script>

<svelte:head>
    <title>Histórico</title>
</svelte:head>

<h1>Histórico</h1>

{#if entries.length > 0}
    <section class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th scope="col">Data</th>
                    <th scope="col">Horário</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Sala</th>
                    <th scope="col">Status</th>
                    <th scope="col">Editar</th>
                </tr>
            </thead>
            <tbody>
                {#each entries as entry, index (entry.startTime)}
                    <tr>
                        <td>
                            <Date date={entry.startTime} />
                        </td>
                        <td>
                            <TimeSpan endTime={entry.endTime} startTime={entry.startTime} />
                        </td>
                        <td>
                            <span id="reservation-name-{index}">{entry.name}</span>
                            <Tooltip target="reservation-name-{index}">
                                {entry.eventDescription}
                            </Tooltip>
                        </td>
                        <td>
                            {entry.roomInfo.buildingNumber} — {entry.roomInfo.roomNumber}
                        </td>
                        <td>
                            <StatusColored status={entry.status} />
                        </td>
                        <td>
                            <a
                                class="btn btn-secondary"
                                class:disabled={!isEditable(entry)}
                                href={`history/edit/${entry.entryMapping.entityId}`}
                            >
                                <i class="bi bi-pencil-square"></i>
                            </a>
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </section>
{:else}
    <p>Você não possui reservas.</p>
{/if}

{#if edits.length > 0}
    <h2 class="mb-3 mt-3">Edições pendentes</h2>

    {#if form !== null}
        {#if form.errors}
            <Alert color="danger">Não foi possível cancelar a edição</Alert>
        {:else}
            <Alert>
                A edição {form.response.eventName} foi cancelada
            </Alert>
        {/if}
    {/if}

    <table class="table table-hover table-striped">
        <thead>
            <th scope="col">Data</th>
            <th scope="col">Horário</th>
            <th scope="col">Nome</th>
            <th scope="col">Sala</th>
            <th scope="col">Cancelar</th>
        </thead>
        <tbody>
            {#each edits as edit, index}
                <tr>
                    <td>
                        <Date date={edit.startTime}></Date>
                    </td>
                    <td>
                        <TimeSpan startTime={edit.startTime} endTime={edit.endTime} />
                    </td>
                    <td>
                        <span id="edit-name-{index}">{edit.name}</span>
                        <Tooltip target="edit-name-{index}">
                            {edit.eventDescription}
                        </Tooltip>
                    </td>
                    <td>
                        {edit.roomInfo.buildingNumber} — {edit.roomInfo.roomNumber}
                    </td>
                    <td>
                        <form method="post" use:enhance>
                            <input
                                type="number"
                                name="editionRequestId"
                                id="editionRequestId"
                                value={edit.entryMapping.entityId}
                                hidden
                            />
                            <button class="btn btn-danger" type="submit" formaction="?/cancel">
                                <i class="bi bi-x-circle" />
                            </button>
                        </form>
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
{/if}