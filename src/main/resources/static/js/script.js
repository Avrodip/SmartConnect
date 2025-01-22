console.log("Script loaded");

// Get the current theme from localStorage and apply it on page load
let currentTheme = getTheme();
document.addEventListener("DOMContentLoaded", () => {
applyTheme(currentTheme);
});

function changeTheme() {
    const changeThemeButton = document.querySelector("#theme_change_button");

    // Add an event listener to the button to toggle the theme
    changeThemeButton.addEventListener("click", () => {
        const oldTheme = currentTheme;

        // Toggle the theme
        currentTheme = currentTheme == "dark" ? "light" : "dark";

        // Save the new theme to localStorage
        setTheme(currentTheme);

        // Apply the theme changes
        document.querySelector('html').classList.remove(oldTheme);
        document.querySelector('html').classList.add(currentTheme);

        // Update the button text
        changeThemeButton.querySelector("span").textContent =
            currentTheme === "light" ? "dark" : "light";
    });
}

// Save the theme to localStorage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Get the theme from localStorage or default to 'light'
function getTheme() {
    return localStorage.getItem("theme") || "dark";
}

// Apply the theme to the page
function applyTheme(theme) {
    const htmlElement = document.querySelector('html');
    console.log("thememertrtrtrtr",theme)
    htmlElement.classList.remove('light', 'dark');
    htmlElement.classList.add(theme);
    // Update the button text based on the current theme
    const changeThemeButton = document.querySelector("#theme_change_button");
    changeThemeButton.querySelector("span").textContent =
        theme == "light" ? "dark" : "light";

    changeTheme();
}
