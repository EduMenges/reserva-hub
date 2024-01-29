<script lang="ts">
    import StatusColored from "$lib/components/StatusColored.svelte";
    import { hours } from "$lib/utils";
    import type { PageServerData } from "./$types";
    export let data: PageServerData;
    import Date from "$lib/components/table/Date.svelte";
    import TimeSpan from "$lib/components/table/TimeSpan.svelte";
</script>

{#if data.history.length > 0}
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
                {#each data.history as entry (entry.startDate)}
                    <tr>
                        <td>
                            <Date date={entry.startDate} />
                        </td>
                        <td>
                            <TimeSpan endTime={entry.endDate} startTime={entry.startDate}/>
                        </td>
                        <td>
                            {entry.name}
                        </td>
                        <td>
                            {entry.roomInfo.buildingNumber} — {entry.roomInfo.roomNumber}
                        </td>
                        <td>
                            <StatusColored status={entry.status} />
                        </td>
                        <td>
                            <button class="btn btn-secondary" disabled={entry.status !== "AWAITING_APPROVAL"}
                                >Editar</button
                            >
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </section>
{:else}
    <p>Você não possui reservas.</p>
{/if}
