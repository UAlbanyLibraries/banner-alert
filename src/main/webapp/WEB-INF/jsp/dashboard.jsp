<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Banner Alerts</title>
		<link rel="stylesheet" href="/host/branded-wrapper/ulib-generic-wrapper.css">
		
		<script src="?type=js" id="javascript"></script>
	</head>
	<body id="banner-alert-dashboard">
		<header>
			<h1>Banner Alerts</h1>
		</header>
		<main>
			<section id="create-form">
				<h2>Create Banner Form</h2>
				
				<form id="banner-alert-form" method="POST">
					<div class="field-wrapper">
						<label for="banner-alert-message">Message <span class="required">(required)</span></label>
						<span>
							<textarea id="banner-alert-message" aria-describedby="banner-alert-message-desc" name="message" autocomplete="off" required maxlength="255"></textarea>
							<span class="instructions" id="banner-alert-message-desc">e.g. All Libraries Closed Today.</span>
							<span id="description-limit-counter"></span>
						</span>
					</div>
					
					<div class="field-wrapper">
						<label for="banner-alert-end">Expiration Date/Time</label>
						<input id="banner-alert-end" aria-describedby="banner-alert-end-desc" name="end" type="date" autocomplete="off" />
						<span class="instructions" id="banner-alert-end-desc">Leave blank for indefinite duration.</span>
					</div>
					
					<div class="field-wrapper vertical">
						<label for="banner-alert-type">Banner Type</label><br />
						<select id="banner-alert-type" name="type" autocomplete="off"></select>
					</div>
					
					<div>
						<button class="primary" id="submit-generate-qr" type="submit" value="">Add Banner Message</button>
						<button class="secondary" id="reset-qr-options" type="reset" value="Reset">Reset</button>
					</div>
				</form>
				
				<div id="message-container" role="alert">
				<ul id="message-list"></ul>
			</div>
			</section>
			
			<section id="live-output">
				<h2>Live Output</h2>
				<div id="banner-alert-output-container"></div>
			</section>
			
			<section id="all-entries">
				<h2>All Entries</h2>
				
				<div id="banner-alert-list-container" class="banner-alert-list-container" aria-atomic="true">
					<ul id="banner-alert-list" class="banner-alert-list"></ul>
				</div>
			</section>
		</main>
	</body>
</html>