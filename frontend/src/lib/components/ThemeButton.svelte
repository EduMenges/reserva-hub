<script lang="ts">
  import { onMount } from "svelte"; 
  interface ThemeOption {
    description: string;
    icon: string;
    set(): void;
  }

  const lightMode: ThemeOption = {
    description: "Claro",
    icon: "sun-fill",
    set() {
      const doc = document.querySelector("html");
      doc?.setAttribute("data-bs-theme", "light");
    },
  };

  const darkMode: ThemeOption = {
    description: "Escuro",
    icon: "moon-stars-fill",
    set() {
      const doc = document.querySelector("html");
      doc?.setAttribute("data-bs-theme", "dark");
    },
  };

  const autoMode: ThemeOption = {
    description: "Automático",
    icon: "circle-half",
    set() {
      if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
        darkMode.set();
      } else {
        lightMode.set();
      }
    },
  };

  let selected: ThemeOption = autoMode;

  onMount(() => {
    selected.set();
  });
</script>

<li class="nav-item dropdown">
  <button class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown">
    <i class={`me-2 bi-${selected.icon}`}></i>
    Tema
  </button>
  <ul class="dropdown-menu">
    {#each [lightMode, darkMode, autoMode] as opt}
      <li>
        <button
          class="dropdown-item"
          type="button"
          on:click={() => {
            selected = opt;
            selected.set();
          }}
        >
          <i class={`me-2 bi-${opt.icon}`}></i>
          {opt.description}
        </button>
      </li>
    {/each}
  </ul>
</li>
