async function loadIntoTable(url, table) {
    const tableBody = table.querySelector("tbody")
    const response = await fetch(url);
    const data = await response.json();

    // Clear the table
    tableBody.innerHTML = "";

    // Populate the rows
    for (const row of data){
        const rowElement = document.createElement("tr");

        for(const cellText of row) {
            const cellElement = document.createElement("td");

            cellElement.textContent = cellText;
            rowElement.appendChild(cellElement);
        }

        tableBody.appendChild(rowElement);
    }
}


loadIntoTable("https://api.alternative.me/v1/ticker/?limit=10", document.querySelector("table"));