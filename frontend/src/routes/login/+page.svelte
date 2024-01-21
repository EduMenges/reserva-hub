<script lang="ts">
    import Logo from "$lib/components/Logo.svelte";
    import { enhance } from "$app/forms";
    import SmallContainer from "$lib/components/SmallContainer.svelte";
    import type { PageData } from "./$types";
    import { superForm } from "sveltekit-superforms/client";
    import SuperDebug from "sveltekit-superforms/client/SuperDebug.svelte";
    import ListErrors from "$lib/components/ListErrors.svelte";
    import { errorSchema, loginSchema } from "$lib/schemas";

    export let data: PageData;

    const { form, errors, constraints, message } = superForm(data.form);
</script>

<svelte:head>
    <title>Login</title>
</svelte:head>

<SmallContainer class="card">
    <header class="card-header text-center">
        <Logo />
        <p>Entre com a sua conta pessoal do INF</p>
    </header>

    <form class="card-body was-validated" method="post" use:enhance>
        {#if $message}
        {console.log($message.errors)}
            {#each $message.errors as errors}
                {errors}
            {/each}
            <ListErrors errors={$message.errors} />
        {/if}

        <div class="mb-3">
            <label class="form-label" for="username">Usuário</label>
            <input
                id="username"
                class="form-control"
                type="text"
                name="username"
                bind:value={$form.username}
                {...$constraints.username}
            />
            <ListErrors errors={$errors.username} />
        </div>

        <div class="mb-3">
            <label class="form-label" for="password">Senha</label>
            <input id="password" class="form-control" type="password" name="password" {...$constraints.password} />
            <ListErrors errors={$errors.password} />
        </div>

        <div class="d-flex justify-content-between flex-wrap">
            <button type="submit" class="btn btn-primary" formaction="?/login">Entrar</button>
            <button type="submit" class="btn btn-danger" formaction="?/as_admin">Entrar como administrador</button>
        </div>
    </form>
</SmallContainer>
