// Local Storage Registry acting as our lightweight, client-side browser database
let resources = JSON.parse(localStorage.getItem('my_links')) || [
    { id: 1, title: "Spring Initializr", url: "https://start.spring.io", category: "backend" },
    { id: 2, title: "MDN Web Docs", url: "https://developer.mozilla.org", category: "frontend" }
];

// Read operation: Iterates over the data list and builds dynamic DOM layouts
function loadResources() {
    const mainContainer = document.getElementById('container');
    mainContainer.innerHTML = ''; 

    // Categorization: Group items using a standard array reduce accumulator
    const groupedData = resources.reduce((groups, item) => {
        const categoryName = item.category ? item.category.trim().toLowerCase() : "general";
        if (!groups[categoryName]) { 
            groups[categoryName] = []; 
        }
        groups[categoryName].push(item);
        return groups;
    }, {});

    // Rendering: Generate independent layout grids for each custom title segment
    for (const category in groupedData) {
        const section = document.createElement('div');
        section.className = 'category-section';
        section.innerHTML = `
            <div class="category-title">${category} (${groupedData[category].length})</div>
            <div class="grid" id="grid-${category}"></div>
        `;
        mainContainer.appendChild(section);

        const grid = document.getElementById(`grid-${category}`);
        groupedData[category].forEach(item => {
            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML = `
                <h3>${item.title}</h3>
                <div class="card-actions">
                    <a href="${item.url}" target="_blank" class="btn-visit">Visit Link</a>
                    <button class="btn-delete" onclick="deleteResource(${item.id})">Delete</button>
                </div>
            `;
            grid.appendChild(card);
        });
    }
}

// Create operation: Validates inputs, creates object items, and updates state strings
function addResource() {
    const title = document.getElementById('title').value.trim();
    const url = document.getElementById('url').value.trim();
    const category = document.getElementById('category').value.trim();

    if(!title || !url || !category) {
        alert('Please fill out all fields');
        return;
    }

    const newResource = { id: Date.now(), title, url, category };
    resources.push(newResource);
    localStorage.setItem('my_links', JSON.stringify(resources));

    // Clear text value variables out of input elements
    document.getElementById('title').value = '';
    document.getElementById('url').value = '';
    document.getElementById('category').value = '';
    
    loadResources();
}

// Delete operation: Filters item indices out of the collection layout array
function deleteResource(id) {
    resources = resources.filter(item => item.id !== id);
    localStorage.setItem('my_links', JSON.stringify(resources));
    loadResources();
}

// Boot loop trigger execution
loadResources();