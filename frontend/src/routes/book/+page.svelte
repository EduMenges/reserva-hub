<script lang="ts">
    import { currentDay } from "$lib/utils";
    import { superForm } from "sveltekit-superforms/client";
    import type { PageData } from "./$types";
    import ListErrors from "$lib/components/ListErrors.svelte";
    import { resolveRoute } from "$app/paths";
    import ListMessages from "$lib/components/ListMessages.svelte";

    export let data: PageData;
    const { enhance, form, errors, constraints, message } = superForm(data.form);
</script>

<svelte:head>
    <title>Buscar Sala</title>
</svelte:head>

{#if data?.user?.role === "STUDENT"}
<div class="alert alert-warning mb-3" role="alert">
    Reservas realizadas por alunos necessitam da aprovação de um administrador.
</div>
{/if}

<form class="was-validated" method="get" action="?/search">
    <ListMessages message={$message} />
    <fieldset name="horario">
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
                <input type="time" class="form-control" name="endTime" id="endTime" {...$constraints.endTime} />
                <ListErrors errors={$errors.endTime} />
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

    <!-- <fieldset name="informacoes">
    <legend>Informações</legend>

    <div class="mb-3">
      <label for="title" class="form-label">Título do evento</label>
      <input type="text" name="title" id="title" class="form-control" required />
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Descrição</label>
      <textarea name="description" id="description" class="form-control" required></textarea>
    </div>
  </fieldset> -->

    <button type="submit" class="btn btn-primary">
        <i class="bi bi-search me-2" />
        Buscar salas
    </button>
</form>

{#if data.rooms}
  
<h1 class="mt-3">Escolha a sala reserva</h1>
{#if data.rooms.length > 0}
  <form use:enhance action="?/allocateRoom" method="post">
    <fieldset>
      <legend>Salas disponíveis</legend>
      {#each data.rooms as room}
        <div class="form-check">
          <input type="radio" class="form-check-input" name="room" id={room.id.toString()} />
          <label for={room.id.toString()} class="form-check-label">
            {room.buildingNumber} | {room.roomNumber} | {room.capacity} |
          </label>
        </div>
      {/each}
    </fieldset>

    <button type="submit" class="btn btn-primary">Efetuar reserva</button>
  </form>
{:else}
  <p>Não há salas disponíveis.</p>
{/if}

{/if}