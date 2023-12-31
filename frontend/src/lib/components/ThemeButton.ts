export interface ThemeOption {
  description: string;
  icon: string;
  set(): void;
}

export const lightMode: ThemeOption = {
  description: "Claro",
  icon: "sun-fill",
  set() {
    const doc = document.querySelector("html");
    doc?.setAttribute("data-bs-theme", "light");
  },
};

export const darkMode: ThemeOption = {
  description: "Escuro",
  icon: "moon-stars-fill",
  set() {
    const doc = document.querySelector("html");
    doc?.setAttribute("data-bs-theme", "dark");
  },
};

export const autoMode: ThemeOption = {
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
