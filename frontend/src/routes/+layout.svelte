<script lang="ts">
  import ThemeButton from "$lib/components/ThemeButton.svelte";
import "../app.scss";
</script>

<nav class="navbar container">
  <ThemeButton />
</nav>

<main class="container">
  <slot />
</main>
