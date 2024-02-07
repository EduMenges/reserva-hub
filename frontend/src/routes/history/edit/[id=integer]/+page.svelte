<script lang="ts">
    import { superForm } from "sveltekit-superforms/client";
    import type { PageData } from "./$types";
    import ListErrors from "$lib/components/ListErrors.svelte";
    import ListMessages from "$lib/components/ListMessages.svelte";

    export let data: PageData;

    const { form, errors, constraints, message, enhance } = superForm(data.form);
</script>

<svelte:head>
    <title>Editar reserva</title>
</svelte:head>

<ListMessages message={$message}/>

<form method="post" use:enhance>
    <fieldset name="time">
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
            </div>

            <div class="mb-3 col">
                <label for="endTime" class="form-label">Fim</label>
                <input
                    type="time"
                    class="form-control"
                    name="endTime"
                    id="endTime"
                    {...$constraints.endTime}
                    bind:value={$form.endTime}
                />
                <ListErrors errors={$errors.endTime} />
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend> Informações </legend>
        <div class="mb-3">
            <label class="form-label" for="eventName">Nome</label>
            <input
                class="form-control"
                type="text"
                name="eventName"
                id="eventName"
                {...$constraints.eventName}
                bind:value={$form.eventName}
            />
        </div>
        <div class="mb-3">
            <label for="eventDescription" class="form-label">Descrição</label>
            <textarea
                name="eventDescription"
                id="eventDescription"
                class="form-control"
                {...$constraints.eventDescription}
                bind:value={$form.eventDescription}
            ></textarea>
        </div>
    </fieldset>

    <input type="number" hidden name="reservationId" bind:value={$form.reservationId} />
    <input type="number" hidden name="roomId" bind:value={$form.roomId} />

    <button class="btn btn-primary" type="submit" formaction="?/edit">Confirmar</button>
    <a class="btn btn-secondary" href="/history">Cancelar</a>
    <button class="btn btn-danger" type="submit" formaction="?/delete" formnovalidate>Excluir</button>
</form>
