async function load_page() {
	document.querySelector('#banner-alert-form').addEventListener('submit', create_banner);
	
	try {
		const response = await fetch(window.location.href, {
				method: 'POST',
				headers: {
					'Accept': 'application/json'
				}
			}
		);
		
		if (!response.ok) {
			throw new Error(`HTTP ${response.status}: ${response.statusText}`);
		}
		
		const data = await response.json();
		
		if (data.error) {
			addErrorMessage(data.error);
		}
		else {
			load_types(data.types);
			load_list(data.list);
		}
	}
	catch (error) {
		addErrorMessage(error);
	}
	
	load_output();
};

async function load_output() {
	try {
		const response = await fetch('/banner-alert/output');
		const text = await response.text();
		document.querySelector('#banner-alert-output-container').insertAdjacentHTML('beforeend', text);
	}
	catch (error) {
		addErrorMessage(error);
	}
};

async function create_banner(e) {
	e.preventDefault();
	
	try {
		const formData = new FormData(e.currentTarget);
		const response = await fetch('/banner-alert/create', {
			method: 'POST',
			body: formData,
			headers: {
				'Accept': 'application/json'
			}
		});
		
		if (!response.ok) {
			throw new Error(`HTTP ${response.status}: ${response.statusText}`);
		}
		
		const data = await response.json();
		
		if (data.error) {
			addErrorMessage(data.error);
		}
		else {
			window.location.reload();
		}
	}
	catch (error) {
		addErrorMessage(error);
	}
};

function addErrorMessage(text) {
	const messageList = document.querySelector('#message-list');
	const li = document.createElement('li');
	li.classList.add("alert alert-danger");
	li.textContent = text;
	
	messageList.appendChild(li);
	console.error(`Request failed: ${text}`);
};

function load_types(types) {
	const select = document.querySelector('#banner-alert-type');
	
	for (let i = 0; i < types.length; i++) {
		select.appendChild(new Option( types[i], i));
	}
};

function load_list(entries) {
	const alertList = document.querySelector('#banner-alert-list');
	alertList.innerHTML = '';
	
	const localeOptions = {
			year : 'numeric',
			month : 'short',
			day : 'numeric'
		};
	
	const fragment = document.createDocumentFragment();
	
	entries.forEach(entry => {
		const li = document.createElement('li');
		li.id = `entry_${entry['id']}`;
		li.innerHTML = `<dl>
				<dt class="banner-entry-message">Message:</dt>
				<dd data-title="Message">${entry['message']}</dd>
				<dt class="banner-entry-netid">NetID:</dt>
				<dd data-title="NetID">${entry['netid']}</dd>
				<dt class="banner-entry-display">Display:</dt>
				<dd data-title="Display">${entry['display']}</dd>
				<dt class="banner-entry-expiration">Expiration</dt>
				<dd data-title="Expiration">${entry['end'] ? new Date(entry['end']).toLocaleString("en-US", localeOptions) : '&mdash;'}</dd>
			</dl>`;
		
		fragment.appendChild(li);
	});
	
	alertList.appendChild(fragment);
};

document.addEventListener('DOMContentLoaded', load_page);