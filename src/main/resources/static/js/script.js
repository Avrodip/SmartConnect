console.log("Script loaded");

let currentTheme = getTheme();
document.addEventListener("DOMContentLoaded", () => {
applyTheme(currentTheme);
});

function changeTheme() {
    const changeThemeButton = document.querySelector("#theme_change_button");

    changeThemeButton.addEventListener("click", () => {
        const oldTheme = currentTheme;

        currentTheme = currentTheme == "dark" ? "light" : "dark";

        setTheme(currentTheme);

        document.querySelector('html').classList.remove(oldTheme);
        document.querySelector('html').classList.add(currentTheme);

        changeThemeButton.querySelector("span").textContent =
            currentTheme === "light" ? "dark" : "light";
    });
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}


function getTheme() {
    return localStorage.getItem("theme") || "dark";
}

function applyTheme(theme) {
    const htmlElement = document.querySelector('html');
    htmlElement.classList.remove('light', 'dark');
    htmlElement.classList.add(theme);
    const changeThemeButton = document.querySelector("#theme_change_button");
    changeThemeButton.querySelector("span").textContent =
        theme == "light" ? "dark" : "light";

    changeTheme();
}
