<script lang="ts">
    import { superForm } from "sveltekit-superforms/client";
    import type { PageData } from "./$types";
    import ListErrors from "$lib/components/ListErrors.svelte";
    import ListMessages from "$lib/components/ListMessages.svelte";
    import SuperDebug from "sveltekit-superforms/client/SuperDebug.svelte";

    export let data: PageData;
    const { form, errors, constraints, message } = superForm(data.searchForm);
    const {
        enhance: bookingEnhance,
        form: bookingForm,
        message: bookingMessage,
        errors: bookingErrors,
        constraints: bookingConstraints,
    } = superForm(data.bookForm);
</script>

<svelte:head>
    <title>Buscar Sala</title>
</svelte:head>

<SuperDebug data={$bookingForm} />

{#if data?.user?.role === "STUDENT"}
    <div class="alert alert-warning mb-3" role="alert">
        Reservas realizadas por alunos necessitam da aprovação de um administrador.
    </div>
{/if}

<form class="was-validated" method="get">
    <ListMessages message={$message} />
    <fieldset name="horario" class="was-validated">
        <legend>Horário</legend>

        <div class="row">
            <div class="mb-3 col-12 col-sm-6">
                <label for="date" class="form-label">Data</label>
                <input
                    type="date"
                    class="form-control"
                    name="date"
                    id="date"
                    {...$constraints.date}
                    bind:value={$form.date}
                />
                <ListErrors errors={$errors.date} />
                <ListErrors errors={$bookingErrors.date} />
            </div>

            <div class="mb-3 col">
                <label for="startTime" class="form-label">Início</label>
                <input
                    type="time"
                    class="form-control"
                    name="startTime"
                    id="startTime"
                    {...$constraints.startTime}
                    bind:value={$form.startTime}
                />
                <ListErrors errors={$errors.startTime} />
                <ListErrors errors={$bookingErrors.startTime} />
            </div>

            <div class="mb-3 col">
                <label for="endTime" class="form-label">Fim</label>
                <input type="time" class="form-control" name="endTime" id="endTime" {...$constraints.endTime} bind:value={$form.endTime} />
                <ListErrors errors={$errors.endTime} />
                <ListErrors errors={$bookingErrors.endTime} />
            </div>
        </div>
    </fieldset>

    <fieldset name="sala">
        <legend>Sala</legend>

        <div class="row">
            <div class="mb-3 col">
                <label for="buildingNumber" class="form-label">Prédio</label><input
                    type="text"
                    class="form-control"
                    id="buildingNumber"
                    name="buildingNumber"
                    {...$constraints.buildingNumber}
                    bind:value={$form.buildingNumber}
                />
                <ListErrors errors={$errors.buildingNumber} />
            </div>
            <div class="mb-3 col">
                <label for="roomNumber" class="form-label">Sala</label><input
                    type="text"
                    class="form-control"
                    id="roomNumber"
                    name="roomNumber"
                    {...$constraints.roomNumber}
                    bind:value={$form.roomNumber}
                />
                <ListErrors errors={$errors.roomNumber} />
            </div>
            <div class="mb-3 col">
                <label for="capacity" class="form-label">Capacidade</label><input
                    type="number"
                    class="form-control"
                    id="capacity"
                    name="capacity"
                    {...$constraints.capacity}
                    bind:value={$form.capacity}
                />
                <ListErrors errors={$errors.capacity} />
            </div>
        </div>
    </fieldset>

    <button type="submit" class="btn btn-primary">
        <i class="bi bi-search me-2" />
        Buscar salas
    </button>
</form>

{#if data.rooms}
    {#if data.rooms.length > 0}
        <form method="post" action="?/allocateRoom" use:bookingEnhance class="mt-2">
            <fieldset name="informacoes">
                <legend>Informações</legend>

                <div class="mb-3">
                    <label for="title" class="form-label">Título do evento</label>
                    <input
                        type="text"
                        name="eventName"
                        id="title"
                        class="form-control"
                        {...$bookingConstraints.eventName}
                        bind:value={$bookingForm.eventName}
                    />
                    <ListErrors errors={$bookingErrors.eventName} />
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Descrição</label>
                    <textarea
                        name="eventDescription"
                        id="description"
                        class="form-control"
                        {...$bookingConstraints.eventDescription}
                        bind:value={$bookingForm.eventDescription}
                    ></textarea>
                    <ListErrors errors={$bookingErrors.eventDescription} />
                </div>
            </fieldset>

            <h2 class="mt-3">Escolha a sala reserva</h2>
            <fieldset>
                <legend>Salas disponíveis</legend>

                {#each data.rooms as room}
                    <div class="form-check">
                        <input
                            type="radio"
                            class="form-check-input"
                            name="roomId"
                            id={room.id.toString()}
                            {...$bookingConstraints.roomId}
                            bind:group={$bookingForm.roomId}
                            value={room.id}
                        />
                        <label for={room.id.toString()} class="form-check-label">
                            {room.buildingNumber} | {room.roomNumber} | {room.capacity} |
                        </label>
                    </div>
                {/each}
                <ListErrors errors={$bookingErrors.roomId} />
            </fieldset>

            <input type="date" name="date" bind:value={$form.date} {...$bookingConstraints.date} hidden />
            <input
                type="time"
                name="startTime"
                bind:value={$form.startTime}
                hidden
                {...$bookingConstraints.startTime}
            />
            <input type="time" name="endTime" bind:value={$form.endTime} {...$bookingConstraints.endTime} hidden />

            <ListMessages message={$bookingMessage} />
            <button type="submit" class="btn btn-primary mt-2">Efetuar reserva</button>
        </form>
    {:else}
        <p>Não há salas disponíveis.</p>
    {/if}
{/if}
