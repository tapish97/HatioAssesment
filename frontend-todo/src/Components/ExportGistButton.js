import React from "react";

const ExportGistButton = ({ project }) => {

  const githubToken = process.env.REACT_APP_GITHUB_TOKEN;
  console.log(githubToken)
  const generateMarkdown = () => {
    const completedTodos = project.todos.filter((todo) => todo.status === "COMPLETED");
    const pendingTodos = project.todos.filter((todo) => todo.status === "PENDING");

    let markdown = `# ${project.title}\n\n`;
    markdown += `**Summary:** ${completedTodos.length} / ${project.todos.length} completed.\n\n`;

    markdown += `## Pending Tasks\n`;
    markdown += pendingTodos.length > 0
      ? pendingTodos.map((todo) => `- [ ] ${todo.description}`).join("\n")
      : "No pending tasks.\n";

    markdown += `\n## Completed Tasks\n`;
    markdown += completedTodos.length > 0
      ? completedTodos.map((todo) => `- [x] ${todo.description}`).join("\n")
      : "No completed tasks.\n";

    return markdown;
  };


  // Function to save the Markdown file locally
  const saveMarkdownFile = (markdownContent, fileName) => {
    const blob = new Blob([markdownContent], { type: "text/markdown" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = `${fileName}.md`;
    link.click();
  };

  
  // Function to create a secret Gist
  const createGist = async (markdownContent) => {
    
    const gistData = {
      description: `Project Summary for ${project.title}`,
      public: false,
      files: {
        [`${project.title}.md`]: {
          content: markdownContent,
        },
      },
    };
    try {
        const response = await fetch("https://api.github.com/gists", {
          method: "POST",
          headers: {
            Authorization: `Bearer ${githubToken}`,
            Accept: "application/vnd.github+json",
            "Content-Type": "application/json",
            "X-GitHub-Api-Version": "2022-11-28",
          },
          body: JSON.stringify(gistData),
        });
  
        // console.log("Response Status:", response.status);
        // console.log("Response Headers:", response.headers);
      // Save the file locally
      saveMarkdownFile(markdownContent, project.title);
      if (!response.ok) {
        throw new Error(` GitHub API error: ${response.statusText} `);
      }

      const data = await response.json();
      alert(`${project.title}.md Downloded, Gist created successfully: ${data.html_url}`);
    } catch (error) {
      console.error("Error creating gist:", error);
      alert(`${project.title}.md Downloded, Failed to create gist. Please check your Github token`);
    }
  };

  
  // Handler for export button click
  const handleExport = async () => {
    const markdownContent = generateMarkdown();
    // Create the gist
    await createGist(markdownContent);
  };

  
  return (
    <button className="btn btn-primary" onClick={handleExport}>
      Export Project as Gist
    </button>
  );



};

export default ExportGistButton;
