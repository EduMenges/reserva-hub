<script lang="ts">
  import { Dropdown, DropdownItem, DropdownMenu, DropdownToggle, NavItem } from "@sveltestrap/sveltestrap";
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

<Dropdown nav>
  <DropdownToggle caret>
    <i class={`me-2 bi-${selected.icon}`}></i>
    Tema
  </DropdownToggle>
  <DropdownMenu>
    {#each [lightMode, darkMode, autoMode] as opt}
      <DropdownItem
        on:click={() => {
          selected = opt;
          selected.set();
        }}
      >
        {opt.description}
      </DropdownItem>
    {/each}
  </DropdownMenu>
</Dropdown>
